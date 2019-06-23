package edu.tsinghua.paratrooper.bl.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SoldierDto {
    private int candidate1Id;
    private String candidate1Name;

    private int candidate2Id;
    private String candidate2Name;
}
