package dev.langchain4j.data.document.splitter;

import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.model.TokenCountEstimator;

public class DocumentSplitters {

    /**
     * This is a recommended {@link DocumentSplitter} for generic text.
     * It tries to split the document into paragraphs first and fits
     * as many paragraphs into a single {@link dev.langchain4j.data.segment.TextSegment} as possible.
     * If some paragraphs are too long, they are recursively split into lines, then sentences,
     * then words, and then characters until they fit into a segment.
     *
     * @param maxSegmentSizeInTokens The maximum size of the segment, defined in tokens.
     * @param maxOverlapSizeInTokens The maximum size of the overlap, defined in tokens.
     *                               Only full sentences are considered for the overlap.
     * @param tokenCountEstimator    The {@code TokenCountEstimator} that is used to count tokens in the text.
     * @return recursive document splitter
     */
    public static DocumentSplitter recursive(int maxSegmentSizeInTokens,
                                             int maxOverlapSizeInTokens,
                                             TokenCountEstimator tokenCountEstimator) {
        return new DocumentByParagraphSplitter(maxSegmentSizeInTokens, maxOverlapSizeInTokens, tokenCountEstimator,
                new DocumentByLineSplitter(maxSegmentSizeInTokens, maxOverlapSizeInTokens, tokenCountEstimator,
                        new DocumentBySentenceSplitter(maxSegmentSizeInTokens, maxOverlapSizeInTokens, tokenCountEstimator,
                                new DocumentByWordSplitter(maxSegmentSizeInTokens, maxOverlapSizeInTokens, tokenCountEstimator)
                        )
                )
        );
    }

    /**
     * This is a recommended {@link DocumentSplitter} for generic text.
     * It tries to split the document into paragraphs first and fits
     * as many paragraphs into a single {@link dev.langchain4j.data.segment.TextSegment} as possible.
     * If some paragraphs are too long, they are recursively split into lines, then sentences,
     * then words, and then characters until they fit into a segment.
     *
     * @param maxSegmentSizeInChars The maximum size of the segment, defined in characters.
     * @param maxOverlapSizeInChars The maximum size of the overlap, defined in characters.
     *                              Only full sentences are considered for the overlap.
     * @return recursive document splitter
     */
    public static DocumentSplitter recursive(int maxSegmentSizeInChars, int maxOverlapSizeInChars) {
        return recursive(maxSegmentSizeInChars, maxOverlapSizeInChars, null);
    }
}
