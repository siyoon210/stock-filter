package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.fnguide;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class FnGuideFinanceTableExtractParam { // FnGuide 메인페이지 Financial Highlight 테이블 파싱용
    public final Set<String> labels;
    public final int elementIndex;

    public static FnGuideFinanceTableExtractParamBuilder builder() {
        return new FnGuideFinanceTableExtractParamBuilder();
    }

    public static class FnGuideFinanceTableExtractParamBuilder {

        private Set<String> labels;
        private int elementIndex;

        FnGuideFinanceTableExtractParamBuilder() {
        }

        public FnGuideFinanceTableExtractParamBuilder labels(String... labels) {
            this.labels = Set.of(labels);
            return this;
        }

        public FnGuideFinanceTableExtractParamBuilder elementIndex(
                int elementIndex) {
            this.elementIndex = elementIndex;
            return this;
        }

        public FnGuideFinanceTableExtractParam build() {
            return new FnGuideFinanceTableExtractParam(labels, elementIndex);
        }

        public String toString() {
            return "FnGuideFinanceTableExtractParam.FnGuideFinanceTableExtractParamBuilder(labels="
                    + this.labels + ", elementIndex=" + this.elementIndex + ")";
        }
    }
}
