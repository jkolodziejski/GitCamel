package com.example.gitcamel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.git.GitConstants;
import org.springframework.stereotype.Component;

@Component
public class RouteGit extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("file:src/main/resources/Test")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        System.out.println(exchange.getIn().getHeaders().get("CamelFileName").toString());
                    }
                })
                .setHeader(GitConstants.GIT_FILE_NAME, constant("test.java"))
                .to("git:///cameltest?operation=add")
                .setHeader(GitConstants.GIT_COMMIT_MESSAGE, constant("first commit"))
                .to("git:///tmp/cameltest?operation=commit")
                .to("git:///tmp/cameltest?operation=push&remotePath=https://ppl-poz-svgit2.psi.de/jkolodziejski/cameltest.git&username=jkolodziejski&password=#Kuba2012Put")
                .to("git:///tmp/cameltest?operation=createTag&tagName=myTag")
                .to("git:///tmp/cameltest?operation=pushTag&tagName=myTag&remoteName=origin");
    }
}
