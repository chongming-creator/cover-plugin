package com.au.coverplugin.report;

import com.au.coverplugin.domain.Coverage;
import com.au.coverplugin.parser.JacocoParser;
import com.au.coverplugin.domain.Git;
import com.au.coverplugin.parser.BaseParser;

import java.io.File;
import java.util.Optional;

/**
 * @Author: ArtificialUnintelligent
 * @Description:
 * @Date: 1:47 PM 2019/1/26
 */
public class JacocoReportFactory extends ReportFactory {

    private static final String REPORT_FILE_DIR = "/site/jacoco";

    private static final String REPORT_INFO = "/jacoco.xml";

    public JacocoReportFactory(String project, Git git, String sourcePath) {
        super(project, git, sourcePath);
    }

    @Override
    String buildSource() {
        return sourcePath + REPORT_FILE_DIR;
    }

    @Override
    Coverage parsingCoverageReport() {
        Coverage coverage = Optional.of(getJacocoParser()).map(BaseParser::parse).orElse(null);
        return coverage;
    }



    @Override
    Coverage parsingHtmlCoverageReport() {
        Coverage coverage = Optional.of(getJacocoHtmlParser().parseHtml(buildSource())).orElse(null);
        return coverage;
    }

    private BaseParser getJacocoHtmlParser(){
        File file = new File(buildSource());
        return new JacocoParser(file);
    }

    private BaseParser getJacocoParser(){
        File file = new File(buildReportIndex());
        return new JacocoParser(file);
    }

    private String buildReportIndex() {
        return buildSource() + REPORT_INFO;
    }
}
