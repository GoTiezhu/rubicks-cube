package com.litiezhu.rubickscube;

import lombok.Data;

import java.util.List;

/**
 * @author Li Kai
 */
@Data
public class CubeDTO {
    List<List<Integer>> cube;
    String action;
}
