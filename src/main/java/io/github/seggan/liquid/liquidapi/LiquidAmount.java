package io.github.seggan.liquid.liquidapi;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LiquidAmount {
    NUGGET(1),
    INGOT(9),
    DUST(9),
    PLATE(72),
    BLOCK(81),
    ;
    private int amount;
}
