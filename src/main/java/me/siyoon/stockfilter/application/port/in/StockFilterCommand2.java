package me.siyoon.stockfilter.application.port.in;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.List;
import lombok.Builder;
import lombok.ToString;
import me.siyoon.stockfilter.domain.Period;

@ToString
@Builder
public class StockFilterCommand2 {

    public final PerCommand per;
    public final PbrCommand pbr;
    public final PcrCommand pcr;
    public final PsrCommand psr;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public StockFilterCommand2(PerCommand per, PbrCommand pbr, PcrCommand pcr, PsrCommand psr) {
        this.per = per;
        this.pbr = pbr;
        this.pcr = pcr;
        this.psr = psr;
    }

    public enum OrderBy {
        DESC, ASC
    }

    public enum InterestedPeriod {
        LAST_QUARTER(Period.LAST_QUARTER),
        LAST_FOUR_QUARTER(Period.FOUR_QUARTERS_AGO, Period.THREE_QUARTERS_AGO,
                          Period.TWO_QUARTERS_AGO, Period.LAST_QUARTER);

        public final List<Period> periods;

        InterestedPeriod(Period... periods) {
            this.periods = List.of(periods);
        }
    }

    @ToString
    public static class PerCommand {

        public final boolean use;
        public final OrderBy orderBy;
        public final InterestedPeriod interestedPeriod;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public PerCommand(boolean use,
                          OrderBy orderBy,
                          InterestedPeriod interestedPeriod) {
            this.use = use;
            this.orderBy = orderBy == null ? OrderBy.ASC : orderBy;
            this.interestedPeriod =
                    interestedPeriod == null ? InterestedPeriod.LAST_QUARTER : interestedPeriod;
        }
    }

    @ToString
    public static class PbrCommand {

        public final boolean use;
        public final OrderBy orderBy;
        public final InterestedPeriod interestedPeriod;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public PbrCommand(boolean use,
                          OrderBy orderBy,
                          InterestedPeriod interestedPeriod) {
            this.use = use;
            this.orderBy = orderBy == null ? OrderBy.ASC : orderBy;
            this.interestedPeriod =
                    interestedPeriod == null ? InterestedPeriod.LAST_QUARTER : interestedPeriod;
        }
    }

    @ToString
    public static class PcrCommand {

        public final boolean use;
        public final OrderBy orderBy;
        public final InterestedPeriod interestedPeriod;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public PcrCommand(boolean use,
                          OrderBy orderBy,
                          InterestedPeriod interestedPeriod) {
            this.use = use;
            this.orderBy = orderBy == null ? OrderBy.ASC : orderBy;
            this.interestedPeriod =
                    interestedPeriod == null ? InterestedPeriod.LAST_QUARTER : interestedPeriod;
        }
    }

    @ToString
    public static class PsrCommand {

        public final boolean use;
        public final OrderBy orderBy;
        public final InterestedPeriod interestedPeriod;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public PsrCommand(boolean use,
                          OrderBy orderBy,
                          InterestedPeriod interestedPeriod) {
            this.use = use;
            this.orderBy = orderBy == null ? OrderBy.ASC : orderBy;
            this.interestedPeriod =
                    interestedPeriod == null ? InterestedPeriod.LAST_QUARTER : interestedPeriod;
        }
    }
}
