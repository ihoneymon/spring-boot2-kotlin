package io.honeymon.boot.springboot2kotlin.documents

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.restdocs.JUnitRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.restdocs.operation.preprocess.Preprocessors.*
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext


@RunWith(SpringRunner::class)
@WebMvcTest
class GreetingControllerDocument {
    /**
     * restDocumentation 을 public 으로 해달라고 요청하는 경우 다음과 같이 처리
     * @see: https://stackoverflow.com/questions/29945087/kotlin-and-new-activitytestrule-the-rule-must-be-public
     */
    @get: Rule
    var restDocumentation = JUnitRestDocumentation()
    @Autowired
    lateinit var wac:WebApplicationContext
    lateinit var mockMvc: MockMvc

    @Before
    fun setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply<DefaultMockMvcBuilder>( // https://medium.com/@thiagotn/testing-and-generate-documentation-for-restful-apis-using-spring-rest-docs-with-kotlin-and-7ba243df26c9
                        documentationConfiguration(this.restDocumentation)
                        .uris()
                        .withScheme("http")
                        .withHost("localhost")
                        .withPort(8080))
                .build()
    }

    @Test
    fun `파라미터가 없는 경우`() {
        mockMvc.perform(get("/greeting")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk)
                .andDo(print())
                .andDo(document("{ClassName}/{methodName}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
    }

    @Test
    fun `파라미터가 있는 경우`() {
        mockMvc.perform(get("/greeting")
                .param("name", "honeymon")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk)
                .andDo(print())
                .andDo(document("{ClassName}/{methodName}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
    }
}