package edu.brown.cs32.live.diplomacy;

/**
 * NOTE: records are a modern Java feature that make defining dataclasses convenient.
 * A record is immutable, and toString(), equals(), etc. are automatically defined.
 *
 * If you like Python, this is similar to @dataclass
 */
public record Diplomat(RANK rank, String name) {

    /**
     *  (Simplified) ambassadorial ranks c. 1961
     *  See: https://en.wikipedia.org/wiki/Diplomatic_rank#Ranks
     */
    public enum RANK {
        NUNCIO, AMBASSADOR, HIGH_COMMISSIONER,
        MINISTER, ENVOY,
        CHARGE_DAFFAIRES
    }

}
