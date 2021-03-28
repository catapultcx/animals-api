package cx.catapult.animals.domain;

/**
 * Amphibians are more generic that 'cat', this is a base implementation of an Amphibian that can be extended for more specific
 * purposes.
 */
public class BaseAmphibian extends BaseAnimal {
    public BaseAmphibian(final String name,
                         final String description) {
        super(name, description, Group.AMPHIBIAN);
    }
}
