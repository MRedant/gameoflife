package be.switchfully.gameoflife.backend.gol;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = GameOfLifeController.WORLD_BASE_URL)
public class GameOfLifeController {

    static final String WORLD_BASE_URL = "/api/gol";
    private static Logger logger = Logger.getLogger(GameOfLifeController.class);

    @PostMapping(value = "/phase")
    public List<List<Boolean>> nextGeneration(@RequestBody List<List<Boolean>> currentWorld) {
        logger.info(currentWorld);

        List<List<Boolean>> newWorld = new ArrayList<>(currentWorld);
        for (int row = 0; row < currentWorld.size(); row++) {
            Boolean[] newRow = new Boolean[currentWorld.size()];
            Arrays.fill(newRow, false);
            newWorld.add(Arrays.asList(newRow));
        }

        int numberOfNeighbours;
        for (int row = 0; row < currentWorld.size(); row++) {
            for (int col = 0; col < currentWorld.size(); col++) {
                numberOfNeighbours = getNeighBoursAlive(currentWorld, row, col);
                if (numberOfNeighbours == 3) {
                    newWorld.get(row).set(col, true);
                }
                if (numberOfNeighbours > 3 || numberOfNeighbours < 2) {
                    newWorld.get(row).set(col, false);
                }
                if (currentWorld.get(row).get(col) && numberOfNeighbours == 2)
                    newWorld.get(row).set(col, true);
            }
        }
        return newWorld;
    }

    public int getNeighBoursAlive(List<List<Boolean>> currentWorld, int row, int col) {
        int numberOfElements = 0;
        if (row > 0 && col > 0 && currentWorld.get(row - 1).get(col - 1)) numberOfElements++;
        if (row > 0 && currentWorld.get(row - 1).get(col)) numberOfElements++;
        if (row > 0 && col < currentWorld.size() - 1 && currentWorld.get(row - 1).get(col + 1)) numberOfElements++;
        if (col > 0 && currentWorld.get(row).get(col - 1)) numberOfElements++;
        if (col < currentWorld.size() - 1 && currentWorld.get(row).get(col + 1)) numberOfElements++;
        if (row < currentWorld.size() - 1 && col > 0 && currentWorld.get(row + 1).get(col - 1)) numberOfElements++;
        if (row < currentWorld.size() - 1 && currentWorld.get(row + 1).get(col)) numberOfElements++;
        if (row < currentWorld.size() - 1 && col < currentWorld.size() - 1 && currentWorld.get(row + 1).get(col + 1))
            numberOfElements++;
        return numberOfElements;
    }

}
