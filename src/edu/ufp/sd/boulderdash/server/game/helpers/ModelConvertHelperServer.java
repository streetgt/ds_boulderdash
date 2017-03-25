package edu.ufp.sd.boulderdash.server.game.helpers;

import edu.ufp.sd.boulderdash.server.game.BoulderModel;
import edu.ufp.sd.boulderdash.server.game.BrickWallModel;
import edu.ufp.sd.boulderdash.server.game.DiamondModel;
import edu.ufp.sd.boulderdash.server.game.DirtModel;
import edu.ufp.sd.boulderdash.server.game.DisplayableElementModel;
import edu.ufp.sd.boulderdash.server.game.EmptyModel;
import edu.ufp.sd.boulderdash.server.game.ExpandingWallModel;
import edu.ufp.sd.boulderdash.server.game.MagicWallModel;
import edu.ufp.sd.boulderdash.server.game.RockfordModel;
import edu.ufp.sd.boulderdash.server.game.SteelWallModel;
import edu.ufp.sd.boulderdash.server.game.exceptions.UnknownModelException;

/**
 * ModelConvertHelper
 *
 * Provides model conversion services.
 *
 * @author Valerian Saliou <valerian@valeriansaliou.name>
 * @since 2015-06-22
 */
public class ModelConvertHelperServer {

    /**
     * Class constructor
     */
    public ModelConvertHelperServer() {
        // Nothing done.
    }

    /**
     * Gets the model associated to the string
     *
     * @param spriteName Sprite name
     * @return Model associated to given sprite name
     */
    public DisplayableElementModel toModel(String spriteName, boolean isConvertible) throws UnknownModelException {
        DisplayableElementModel element;

        //System.out.println("[DEBUG]: ModelConverterHelper.toModel - " + spriteName);
        // Instanciates the sprite element matching the given sprite name
        switch (spriteName) {
            case "black":
            case "Black":
                element = new EmptyModel();
                break;

            case "boulder":
            case "Boulder":
                element = new BoulderModel(isConvertible);
                break;

            case "brickwall":
            case "Brick Wall":
                element = new BrickWallModel();
                break;

            case "diamond":
            case "Diamond":
                element = new DiamondModel();
                break;

            case "dirt":
            case "Dirt":
                element = new DirtModel();
                break;

            case "magicwall":
            case "Magic Wall":
                element = new MagicWallModel();
                break;

            case "rockford":
            case "Rockford":
            case "rockford2":
            case "Rockford2":
                System.out.println("[DEBUG]: ModelConvertHelper - SpriteName: " + spriteName);
                element = new RockfordModel(spriteName);
                break;

            case "steelwall":
            case "Steel Wall":
                element = new SteelWallModel();
                break;

            case "expandingwall":
            case "Expanding Wall":
                element = new ExpandingWallModel();
                break;

            default:
                throw new UnknownModelException("Unknown model element > " + spriteName);
        }

        return element;
    }

    /**
     * Gets the string associated to the model
     *
     * @return Model string name
     */
    public String toString(DisplayableElementModel model) {
        return model.getSpriteName();
    }
}
