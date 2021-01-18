package com.litiezhu.rubickscube;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Li Kai
 */
@Service
public class CubeService {
    public Cube twist(CubeDTO cubeDTO) {
        Cube oldCube = dtoToCube(cubeDTO);
        return oldCube.twist(cubeDTO.getAction());
    }

    private Cube dtoToCube(CubeDTO cubeDTO) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < cubeDTO.getCube().size(); i++) {
            map.put(i, cubeDTO.getCube().get(i));
        }
        return new Cube(map);
    }
}
