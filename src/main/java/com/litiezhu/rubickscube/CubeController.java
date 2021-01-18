package com.litiezhu.rubickscube;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Li Kai
 */
@RestController
public class CubeController {
    @Autowired
    private CubeService cubeService;

    @RequestMapping("/test")
    public String test() {
        return "success";
    }


    @RequestMapping("/testa")
    @ResponseBody
    public int[][] testA(@RequestBody CubeDTO cubeDTO) {
        System.out.println(cubeDTO.getCube());
        System.out.println(cubeDTO.getAction());
        return new int[][]{{0, 1}, {2, 3}};
    }

    @RequestMapping(value = "/twist", method = RequestMethod.POST)
    @ResponseBody
    public int[][] twist(@RequestBody CubeDTO cubeDTO) {
        System.out.println("NEW POST");
        System.out.println(cubeDTO.getCube());
        System.out.println(cubeDTO.getAction());
        Cube cube = cubeService.twist(cubeDTO);
        Map<Integer, List<Integer>> map = cube.getFaces();
        int[][] res = new int[6][9];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 9; j++) {
                res[i][j] = map.get(i).get(j);
            }
        }
        return res;
    }
}
