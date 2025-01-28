package edu.brown.cs32.live.diplomacy;

import java.util.Comparator;

// Avoid the need to precede ranks with "Diplomat.RANK."
import edu.brown.cs32.live.diplomacy.Diplomat.Rank;

/**
 * Comparator that implements the order of precedence for members of a diplomatic corps.
 * This can determine (e.g.) seating order, or who among a group is spoken to first.
 *
 * The rules vary by country, and this isn't a faithful implementation; don't use this code
 * to seat real diplomats at a real banquet!
 */

public class PrecedenceComparator implements Comparator<Diplomat> {

    /**
     * If present, a diplomatic rank to always give precedence over others.
     *
     * We can just use null _internally_ to represent no precedence rank.
     * If you're curious about other ways to do this, look into (e.g.) Optional.
     */
    private final Rank givePrecedence;

    /**
     *  Create an order of precedence where a certain diplomatic rank is given precedence over all others.
     *  When this rank is not involved, the standard precedence is used.
     *
     *  Real world example:
     *  According to the Vienna Convention, nuncios (ecclesiastical diplomats from the Holy See), have equal
     *  rank to ambassadors. However, the host country is allowed to grant seniority to a nuncio over others.
     * @param givePrecedence diplomatic title that should always be treated as senior
     */
    public PrecedenceComparator(Rank givePrecedence) {
        if(givePrecedence == null)
            throw new IllegalArgumentException(
                    "givePrecedence field of 1-argument constructor must be non-null; use "+
                    "the 0-argument constructor to not automatically prefer any rank.");
        this.givePrecedence = givePrecedence;
    }

    /**
     * Create an order of precedence where the standard precedence is used.
     *
     * NOTE: I chose constructor overloading rather than making the caller provide "null"
     *   themselves, which would add extra clutter for them to deal with and expose our
     *   implementation choices (which we might want to change in future).
     */
    public PrecedenceComparator() {
        this.givePrecedence = null;
    }

    @Override
    public int compare(Diplomat o1, Diplomat o2) {
        // By documentation (see mouseover): Negative if o1 < o2; zero if o1 == o2; positive if o1 > o2
        //   the sign of compare(x,y) must be the same as compare(y,x), and compare must be transitive.

        // new Diplomat(null, "James Bond")
        // one comparator: this.givePrecedence = null
        // another: this.givePrecedence = NUNCIO
        if(o1.rank() == this.givePrecedence) return -1;
        if(o2.rank() == this.givePrecedence) return 1;

        // NOTE: I personally like Java 17's pattern-matching switch expressions
        // for these situations. I find them easier to read than complicated
        // if-statements, and I will get a warning if I don't give a case for
        // every possible enum value. That means I can *LEAVE OUT* the "default"
        // case and trust the compiler to warn me if I ever add a new value to
        // this enum. "Default" cases check at runtime, and can lie around for
        // years and cause subtle bugs when new values get added.

        return switch(o1.rank()) {
            case NUNCIO, AMBASSADOR, HIGH_COMMISSIONER ->
                    switch(o2.rank()) {
                        case NUNCIO, AMBASSADOR, HIGH_COMMISSIONER -> 0; // same
                        case ENVOY, MINISTER, CHARGE_DAFFAIRES -> -1;    // o2 goes first
                    };
            case MINISTER, ENVOY ->
                    switch(o2.rank()) {
                        case NUNCIO, AMBASSADOR, HIGH_COMMISSIONER -> 1; // o1 goes first
                        case ENVOY, MINISTER -> 0;                       // same
                        case CHARGE_DAFFAIRES -> -1;                     // o2 goes first
                    };
            case CHARGE_DAFFAIRES ->
                    switch(o2.rank()) {
                        case NUNCIO, AMBASSADOR, HIGH_COMMISSIONER, ENVOY, MINISTER ->
                                1;                  // o1 goes first
                        case CHARGE_DAFFAIRES -> 0; // same
                    };
        };
    }
}
