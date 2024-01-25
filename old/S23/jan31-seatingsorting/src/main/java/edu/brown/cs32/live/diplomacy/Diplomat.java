package edu.brown.cs32.live.diplomacy;

/**
 * NOTE: records are a modern Java feature that make defining dataclasses convenient.
 * A record is immutable, and toString(), equals(), etc. are automatically defined.
 * We also get a constructor for free. However...
 *
 * Will throw IllegalArgumentException if rank or name is null.
 */
public record Diplomat(Rank rank, String name) {

    /**
     *  (Simplified) ambassadorial ranks c. 1961
     *  See: https://en.wikipedia.org/wiki/Diplomatic_rank#Ranks
     */
    public enum Rank {
        NUNCIO, AMBASSADOR, HIGH_COMMISSIONER,
        MINISTER, ENVOY,
        CHARGE_DAFFAIRES
    }

    /**
     * Define the constructor ourselves (don't use the one that "record" gives
     * us) so that we can validate input values. This is called a *campact*
     * constructor, and works only for records. Note that it's _not_ the same
     * as a 0-argument constructor Diplomat().
     */
    public Diplomat {
        // Values are initialized by the record infrastructure. But we want extra validation:
        if (rank == null || name == null)
            throw new IllegalArgumentException("A diplomat must have non-null rank and name.");
    }

}











/**
 * Define the constructor ourselves (don't use the one that "record" gives
 * us) so that we can validate input values. This is called a *campact*
 * constructor, and works only for records. Note that it's _not_ the same
 * as a 0-argument constructor Diplomat().
 */
//    public Diplomat {
//        // Values are initialized by the record infrastructure. But we want extra validation:
//        if (rank == null || name == null)
//            throw new IllegalArgumentException("A diplomat must have non-null rank and name.");
//    }

