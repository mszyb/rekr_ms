package recruitment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Wall implements Structure {

    private List<Block> blocks;
    private List<Block> singleBlocks;
    private List<CompositeBlock> compositeBlocks;

    public Wall(List<Block> blocks) {
        this.blocks = blocks;
        singleBlocks = new ArrayList<>();
        compositeBlocks = new ArrayList<>();
        extractCompositeBlocks();
    }

    private void extractCompositeBlocks() {
        for (Block block : blocks) {
            if (block instanceof CompositeBlock) {
                compositeBlocks.add((CompositeBlock) block);
            } else {
                singleBlocks.add(block);
            }
        }
    }

    @Override
    public Optional<Block> findBlockByColor(String color) {
        List<Block> colorFilteredBlocks = new ArrayList<>();
        singleBlocks
                .stream()
                .filter(block -> block.getColor().equalsIgnoreCase(color))
                .forEach(colorFilteredBlocks::add);
        compositeBlocks
                .forEach(compositeBlock -> compositeBlock.getBlocks()
                        .stream()
                        .filter(block -> block.getColor().equalsIgnoreCase(color))
                        .forEach(colorFilteredBlocks::add));
        return colorFilteredBlocks.stream().findAny();
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        List<Block> materialFilteredBlocks = new ArrayList<>();
        singleBlocks
                .stream()
                .filter(block -> block.getMaterial().equalsIgnoreCase(material))
                .forEach(materialFilteredBlocks::add);
        compositeBlocks
                .forEach(compositeBlock -> compositeBlock.getBlocks()
                        .stream()
                        .filter(block -> block.getMaterial().equalsIgnoreCase(material))
                        .forEach(materialFilteredBlocks::add));
        return materialFilteredBlocks;
    }

    @Override
    public int count() {
        int counter = 0;
        for (CompositeBlock compositeBlock : compositeBlocks) {
            counter += compositeBlock.getBlocks().size();
        }
        return counter + singleBlocks.size();
    }
}
