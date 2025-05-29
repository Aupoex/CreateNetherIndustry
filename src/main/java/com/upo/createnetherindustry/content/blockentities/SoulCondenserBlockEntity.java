package com.upo.createnetherindustry.content.blockentities;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.api.stress.BlockStressValues;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.fluid.SmartFluidTank;
import com.upo.createnetherindustry.content.recipes.condenser.CondenserRecipeType;
import com.upo.createnetherindustry.content.recipes.condenser.ICondensingRecipe;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import com.simibubi.create.foundation.utility.CreateLang;
import org.jetbrains.annotations.Nullable;
import java.util.List;
import java.util.Optional;

public class SoulCondenserBlockEntity extends KineticBlockEntity implements IHaveGoggleInformation {

    private static final int INPUT_TANK_CAPACITY = 4000;
    private static final int OUTPUT_TANK_CAPACITY = 4000;
    private static final int DEFAULT_PROCESSING_TIME_FALLBACK = 5120;

    protected SmartFluidTankBehaviour inputTankBehaviour;
    protected SmartFluidTankBehaviour outputTankBehaviour;
    private int progress = 0;

    @Nullable
    private RecipeHolder<ICondensingRecipe> currentActiveRecipeHolder = null;
    private int currentRecipeProcessingTime = 0;

    public SoulCondenserBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        inputTankBehaviour = new SmartFluidTankBehaviour(SmartFluidTankBehaviour.INPUT, this, 1, INPUT_TANK_CAPACITY, false)
                .allowInsertion().forbidExtraction()
                .whenFluidUpdates(() -> {
                    onInputTankContentsChanged();
                    setChanged();
                    if (this.level != null && !this.level.isClientSide) {
                        this.level.blockUpdated(this.worldPosition, getBlockState().getBlock());
                    }
                });
        behaviours.add(inputTankBehaviour);

        outputTankBehaviour = new SmartFluidTankBehaviour(SmartFluidTankBehaviour.OUTPUT, this, 1, OUTPUT_TANK_CAPACITY, false)
                .allowExtraction().forbidInsertion()
                .whenFluidUpdates(() -> {
                    setChanged();
                    if (this.level != null && !this.level.isClientSide) {
                        this.level.blockUpdated(this.worldPosition, getBlockState().getBlock());
                    }
                });
        behaviours.add(outputTankBehaviour);
    }

    private void onInputTankContentsChanged() {
        if (this.level == null || this.level.isClientSide) return;

        if (this.progress > 0 && this.currentActiveRecipeHolder != null) {
            FluidStack currentInputFluid = this.inputTankBehaviour != null ? this.inputTankBehaviour.getPrimaryHandler().getFluid() : FluidStack.EMPTY;
            if (!matchRecipeInput(this.currentActiveRecipeHolder.value(), currentInputFluid)) {
                this.progress = 0;
            }
        }
    }

    @Nullable
    public IFluidHandler getFluidHandlerCapability(@Nullable Direction side) {
        if (side == Direction.DOWN) return inputTankBehaviour != null ? inputTankBehaviour.getCapability() : null;
        if (side == Direction.UP) return outputTankBehaviour != null ? outputTankBehaviour.getCapability() : null;
        return null;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level == null || this.level.isClientSide) return;

        if (getSpeed() == 0) {
            return;
        }

        if (this.progress == 0) {

            if (currentActiveRecipeHolder != null) {
                clearActiveRecipeState();
            }

            if (tryPrepareAndStartNextRecipe()) {
                setChanged();
            } else {
                if (currentActiveRecipeHolder != null) clearActiveRecipeState();
                return;
            }
        }

        if (this.progress > 0 && this.currentActiveRecipeHolder != null) {

            this.progress += Math.abs(getSpeed());

            if (this.progress >= this.currentRecipeProcessingTime) {
                completeRecipeCycleAndReset();
            }
            setChanged();
        } else if (this.progress > 0 && this.currentActiveRecipeHolder == null) {
            this.progress = 0;
            clearActiveRecipeState();
            setChanged();
        }
    }

    private boolean tryPrepareAndStartNextRecipe() {
        if (this.inputTankBehaviour == null || this.outputTankBehaviour == null || this.level == null) return false;
        FluidStack inputInTank = this.inputTankBehaviour.getPrimaryHandler().getFluid();
        if (inputInTank.isEmpty()) return false;

        RecipeManager recipeManager = this.level.getRecipeManager();
        Optional<RecipeHolder<ICondensingRecipe>> recipeHolderOpt = recipeManager
                .getAllRecipesFor(CondenserRecipeType.SOUL_CONDENSING_RECIPE_TYPE_DEFERRED.get())
                .stream()
                .filter(holder -> matchRecipeInput(holder.value(), inputInTank))
                .findFirst();

        if (recipeHolderOpt.isPresent()) {
            RecipeHolder<ICondensingRecipe> holder = recipeHolderOpt.get();
            ICondensingRecipe recipe = holder.value();
            FluidStack recipeOutputFluid = recipe.getOutputFluid();
            FluidStack recipeInputToConsume = recipe.getInputFluid();
            SmartFluidTank outputTank = this.outputTankBehaviour.getPrimaryHandler();

            if ((outputTank.getFluid().isEmpty() || outputTank.getFluid().getFluid().isSame(recipeOutputFluid.getFluid())) &&
                    outputTank.getSpace() >= recipeOutputFluid.getAmount()) {

                this.currentActiveRecipeHolder = holder;
                this.currentRecipeProcessingTime = recipe.getProcessingDuration();
                if (this.currentRecipeProcessingTime <= 0) this.currentRecipeProcessingTime = DEFAULT_PROCESSING_TIME_FALLBACK;

                this.inputTankBehaviour.getPrimaryHandler().drain(recipeInputToConsume.copy(), IFluidHandler.FluidAction.EXECUTE);
                this.progress = 1;
                return true;
            }
        }
        return false;
    }

    private void completeRecipeCycleAndReset() {
        if (this.currentActiveRecipeHolder != null && this.outputTankBehaviour != null) {
            ICondensingRecipe recipe = this.currentActiveRecipeHolder.value();
            FluidStack recipeOutputFluid = recipe.getOutputFluid();
            SmartFluidTank outputTank = this.outputTankBehaviour.getPrimaryHandler();

            if ((outputTank.getFluid().isEmpty() || outputTank.getFluid().getFluid().isSame(recipeOutputFluid.getFluid())) &&
                    outputTank.getSpace() >= recipeOutputFluid.getAmount()) {
                this.outputTankBehaviour.getPrimaryHandler().fill(recipeOutputFluid.copy(), IFluidHandler.FluidAction.EXECUTE);
            }
        }
        this.progress = 0;
        clearActiveRecipeState();
    }

    private boolean matchRecipeInput(@Nullable ICondensingRecipe recipe, FluidStack inputInTank) {
        if (recipe == null || inputInTank == null || inputInTank.isEmpty()) return false;
        FluidStack requiredInput = recipe.getInputFluid();
        if (requiredInput == null || requiredInput.isEmpty()) return false;
        return inputInTank.getFluid().isSame(requiredInput.getFluid()) &&
                inputInTank.getAmount() >= requiredInput.getAmount();
    }

    private void clearActiveRecipeState() {
        boolean changed = (this.currentActiveRecipeHolder != null);
        this.currentActiveRecipeHolder = null;
        this.currentRecipeProcessingTime = 0;
        if (changed) {
            setChanged();
        }
    }

    @Override
    protected void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(compound, registries, clientPacket);
        compound.putInt("Progress", progress);
        if (currentActiveRecipeHolder != null) {
            compound.putString("ActiveRecipeId", currentActiveRecipeHolder.id().toString());
            compound.putInt("ActiveRecipeTime", currentRecipeProcessingTime);
        }
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(compound, registries, clientPacket);
        progress = compound.getInt("Progress");
        currentActiveRecipeHolder = null;
        currentRecipeProcessingTime = 0;

        if (compound.contains("ActiveRecipeId")) {
            ResourceLocation recipeId = ResourceLocation.tryParse(compound.getString("ActiveRecipeId"));
            if (recipeId != null) {
                this.currentRecipeProcessingTime = compound.getInt("ActiveRecipeTime");
            }
        }
        if (progress > 0 && !compound.contains("ActiveRecipeId")) {
            progress = 0;
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (this.level != null && !this.level.isClientSide && this.progress > 0 && this.currentActiveRecipeHolder == null) {
            CompoundTag nbt = getUpdateTag(this.level.registryAccess());
            if (nbt.contains("ActiveRecipeId")) {
                ResourceLocation recipeId = ResourceLocation.tryParse(nbt.getString("ActiveRecipeId"));
                if (recipeId != null) {
                    Optional<? extends RecipeHolder<?>> recipeOpt = this.level.getRecipeManager().byKey(recipeId);
                    if (recipeOpt.isPresent() && recipeOpt.get().value() instanceof ICondensingRecipe recipeValue) {
                        this.currentActiveRecipeHolder = new RecipeHolder<>(recipeId, recipeValue);
                        if (this.currentRecipeProcessingTime <= 0) {
                            this.currentRecipeProcessingTime = recipeValue.getProcessingDuration();
                            if(this.currentRecipeProcessingTime <=0) this.currentRecipeProcessingTime = DEFAULT_PROCESSING_TIME_FALLBACK;
                        }
                    } else {
                        this.progress = 0;
                        clearActiveRecipeState();
                    }
                } else {
                    this.progress = 0;
                    clearActiveRecipeState();
                }
            } else {
                this.progress = 0;
                clearActiveRecipeState();
            }
        }
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        boolean addedSomething = false;


        CreateLang.translate("goggle.soul_condenser.title")
                .style(ChatFormatting.WHITE)
                .forGoggles(tooltip);


        if (inputTankBehaviour != null) {
            addedSomething |= addTankToTooltip(tooltip, isPlayerSneaking,
                    inputTankBehaviour.getPrimaryHandler(),
                    "goggle.soul_condenser.input_tank",
                    ChatFormatting.GOLD);
        }
        if (outputTankBehaviour != null) {
            addedSomething |= addTankToTooltip(tooltip, isPlayerSneaking,
                    outputTankBehaviour.getPrimaryHandler(),
                    "goggle.soul_condenser.output_tank",
                    ChatFormatting.AQUA);
        }

        CreateLang.translate("tooltip.separator").forGoggles(tooltip);
        addedSomething = true;

        float speed = Math.abs(getSpeed());
        CreateLang.translate("tooltip.speed", String.format("%.1f", speed) + " RPM")
                .style(speed > 0 ? ChatFormatting.GREEN : ChatFormatting.DARK_GRAY)
                .forGoggles(tooltip, 1); // 缩进1层

        if (level != null && getBlockState().getBlockHolder().isBound()) {
            double baseStressImpact = BlockStressValues.getImpact(getBlockState().getBlock());
            if (speed > 0 && baseStressImpact > 0) {
                CreateLang.translate("goggle.stress_consumption", String.format("%.2f", baseStressImpact * speed))
                        .style(ChatFormatting.GREEN)
                        .forGoggles(tooltip, 1);
            } else if (baseStressImpact > 0) {
                CreateLang.translate("goggle.stress_impact", String.format("%.1f", baseStressImpact))
                        .style(ChatFormatting.DARK_GRAY)
                        .forGoggles(tooltip, 1);
            }
        }
        addedSomething = true;


        if (speed > 0) {
            if (progress > 0 && currentActiveRecipeHolder != null) {
                CreateLang.translate("goggle.soul_condenser.status.processing")
                        .style(ChatFormatting.YELLOW)
                        .forGoggles(tooltip, 1);
            } else if (canPotentiallyStartAnyRealRecipe()) {
                CreateLang.translate("goggle.soul_condenser.status.can_process")
                        .style(ChatFormatting.AQUA)
                        .forGoggles(tooltip, 1);
            } else if (inputTankBehaviour != null && !inputTankBehaviour.getPrimaryHandler().getFluid().isEmpty()){
                CreateLang.translate("goggle.soul_condenser.status.no_recipe_for_input")
                        .style(ChatFormatting.GOLD)
                        .forGoggles(tooltip, 1);
            } else {
                CreateLang.translate("goggle.soul_condenser.status.no_input")
                        .style(ChatFormatting.RED)
                        .forGoggles(tooltip, 1);
            }
        } else { // 速度为0
            CreateLang.translate("goggle.soul_condenser.status.stopped")
                    .style(ChatFormatting.RED)
                    .forGoggles(tooltip, 1);
        }

        return true;
    }

    protected boolean addTankToTooltip(List<Component> tooltip, boolean isPlayerSneaking,
                                       SmartFluidTank tank, String tankLabelKey, ChatFormatting fluidColor) {
        if (tank == null) return false;

        MutableComponent label = CreateLang.translateDirect(tankLabelKey);
        FluidStack fluidStack = tank.getFluid();

        if (fluidStack.isEmpty()) {
            CreateLang.builder().space()
                    .add(label.withStyle(ChatFormatting.GRAY).append(Component.literal(": ")))
                    .space()
                    .add(CreateLang.translateDirect("goggle.fluid.empty").withStyle(ChatFormatting.DARK_GRAY))
                    .forGoggles(tooltip);
        } else {
            CreateLang.builder().space()
                    .add(label.withStyle(ChatFormatting.GRAY).append(Component.literal(": ")))
                    .space()
                    .add(CreateLang.fluidName(fluidStack)
                            .add(Component.literal(" "))
                            .add(CreateLang.text(String.format("%,d", fluidStack.getAmount()))
                                    .style(fluidColor)
                                    .add(CreateLang.text(" / ").style(ChatFormatting.GRAY))
                                    .add(CreateLang.text(String.format("%,d", tank.getCapacity())).style(ChatFormatting.DARK_GRAY))
                                    .add(CreateLang.text(" mB").style(ChatFormatting.GRAY))
                            )
                    )
                    .forGoggles(tooltip);
        }
        return true;
    }


    private boolean canPotentiallyStartAnyRealRecipe() {
        if (inputTankBehaviour == null || outputTankBehaviour == null || level == null) return false;
        FluidStack inputInTank = inputTankBehaviour.getPrimaryHandler().getFluid();
        if (inputInTank.isEmpty()) return false;

        RecipeManager recipeManager = level.getRecipeManager();
        return recipeManager
                .getAllRecipesFor(CondenserRecipeType.SOUL_CONDENSING_RECIPE_TYPE_DEFERRED.get())
                .stream()
                .anyMatch(holder -> {
                    ICondensingRecipe recipe = holder.value();
                    FluidStack requiredInput = recipe.getInputFluid();
                    if (inputInTank.getFluid().isSame(requiredInput.getFluid()) && inputInTank.getAmount() >= requiredInput.getAmount()) {
                        FluidStack recipeOutputFluid = recipe.getOutputFluid();
                        SmartFluidTank outputTank = outputTankBehaviour.getPrimaryHandler();
                        return (outputTank.getFluid().isEmpty() || outputTank.getFluid().getFluid().isSame(recipeOutputFluid.getFluid())) &&
                                outputTank.getSpace() >= recipeOutputFluid.getAmount();
                    }
                    return false;
                });
    }

    public SmartFluidTankBehaviour getInputTankBehaviour() {
        return inputTankBehaviour;
    }
}




