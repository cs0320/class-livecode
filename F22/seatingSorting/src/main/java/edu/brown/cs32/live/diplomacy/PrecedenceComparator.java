package edu.brown.cs32.live.diplomacy;

import java.util.Comparator;
import java.util.Optional;

// Avoid the need to precede ranks with "Diplomat.RANK."
import edu.brown.cs32.live.diplomacy.Diplomat.RANK;

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
     * NOTE: We could have just used "null" to represent the case where this isn't present.
     *   What are the plusses and minuses of this design choice?
     *   Could it be that there are multiple "OK" answers?
     */
    private final Optional<RANK> givePrecedence;

    /**
     *  Create an order of precedence where a certain diplomatic rank is given precedence over all others.
     *  When this rank is not involved, the standard precedence is used.
     *
     *  Real world example:
     *  According to the Vienna Convention, nuncios (ecclesiastical diplomats from the Holy See), have equal
     *  rank to ambassadors. However, the host country is allowed to grant seniority to a nuncio over others.
     * @param givePrecedence diplomatic title that should always be treated as senior
     */
    public PrecedenceComparator(Diplomat.RANK givePrecedence) {
        if(givePrecedence == null)
            throw new IllegalArgumentException("givePrecedence field of 1-argument constructor must be non-null; use" +
                    " the 0-argument constructor to not automatically prefer any rank.");
        this.givePrecedence = Optional.of(givePrecedence);
    }

    /**
     * Create an order of precedence where the standard precedence is used.
     *
     * NOTE: I chose constructor overloading rather than making the caller provide an optional themselves, which
     *   would add extra clutter for them to deal with. My choice to use Optional internally isn't their business.
     */
    public PrecedenceComparator() {
        this.givePrecedence = Optional.empty();
    }

    @Override
    public int compare(Diplomat o1, Diplomat o2) {
        // By documentation (see mouseover): Negative if o1 < o2; zero if o1 == o2; positive if o1 > o2
        //   the sign of compare(x,y) must be the same as compare(y,x), and compare must be transitive.

        if(givePrecedence.isPresent() && givePrecedence.get() == o1.rank()) return 1;
        if(givePrecedence.isPresent() && givePrecedence.get() == o2.rank()) return -1;

        // NOTE: I personally like Java 17's pattern-matching switch expressions for these situations.
        // I find them easier to read than complicated if-statements, and I will get a warning if I don't give a case
        // for every possibile enum value. That means I can *LEAVE OUT* the "default" case and trust the compiler to
        // warn me if I ever add a new value to this enum. "Default" cases check at runtime, and can lie around for
        // years and cause subtle bugs when new values get added.

        return switch(o1.rank()) {
            case NUNCIO, AMBASSADOR, HIGH_COMMISSIONER ->
                    switch(o2.rank()) {
                        case NUNCIO, AMBASSADOR, HIGH_COMMISSIONER -> 0;
                        case ENVOY, MINISTER, CHARGE_DAFFAIRES -> 1;
                    };
            case MINISTER, ENVOY ->
                    switch(o2.rank()) {
                        case NUNCIO, AMBASSADOR, HIGH_COMMISSIONER -> -1;
                        case ENVOY, MINISTER -> 0;
                        case CHARGE_DAFFAIRES -> 1;
                    };
            case CHARGE_DAFFAIRES ->
                    switch(o2.rank()) {
                        case NUNCIO, AMBASSADOR, HIGH_COMMISSIONER, ENVOY, MINISTER -> -1;
                        case CHARGE_DAFFAIRES -> 0;
                    };
        };
    }
}
