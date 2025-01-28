package edu.brown.cs32.live.diplomacy;

/**
 * We are deliberately looking at a domain that most of you will be unfamiliar with,
 * so that you won't have existing preconceptions of what is "right" for sorting here.
 *
 * NOTE: records are a modern Java feature that make defining dataclasses convenient.
 * A record is immutable, and toString(), equals(), etc. are automatically defined.
 *
 */
public record Diplomat(Rank rank, String name) {

    /**
     *  (Simplified) ambassadorial ranks c. 1961
     *  See: <a href="https://en.wikipedia.org/wiki/Diplomatic_rank#Ranks">...</a>
     */
    public enum Rank {
        NUNCIO, AMBASSADOR, HIGH_COMMISSIONER,
        MINISTER, ENVOY,
        CHARGE_DAFFAIRES
    }

}



////////////////////////////
// *** SPOILERS BELOW *** //
////////////////////////////









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

