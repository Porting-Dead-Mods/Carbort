package com.leclowndu93150.carbort.registries;

import com.leclowndu93150.carbort.Carbort;
import com.leclowndu93150.carbort.content.fluids.VoidFluid;
import com.portingdeadmods.portingdeadlibs.utils.FluidRegistrationHelper;

public final class CBFluids {
    public static final FluidRegistrationHelper HELPER = new FluidRegistrationHelper(CBBlocks.BLOCKS, CBItems.ITEMS, Carbort.MODID);

    public static final VoidFluid VOID = HELPER.registerFluid(new VoidFluid("void"));

}
