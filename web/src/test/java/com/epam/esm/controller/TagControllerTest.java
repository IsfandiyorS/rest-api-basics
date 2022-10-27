package com.epam.esm.controller;

import com.epam.esm.dto.impl.TagCreateDto;
import com.epam.esm.dto.impl.TagDto;
import com.epam.esm.service.impl.TagServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration
@WebMvcTest(controllers = TagController.class)
@ExtendWith(SpringExtension.class)
//@RunWith(SpringRunner.class)
//@AutoConfigureMockMvc
//@SpringBootTest(classes = TagController.class)
class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TagController controller;

    @Mock
    private TagServiceImpl tagService = Mockito.mock(TagServiceImpl.class);
    private List<TagCreateDto> tagCreateDtoList;
    private List<TagDto> tagDtoList;

    @BeforeEach
    void setUp() {
        this.tagCreateDtoList = List.of(
                new TagCreateDto("tag2"),
                new TagCreateDto("tag3"),
                new TagCreateDto("tag4"),
                new TagCreateDto("tag5")
        );

        this.tagDtoList = List.of(
                new TagDto(1L, "tag1"),
                new TagDto(2L, "tag2"),
                new TagDto(3L, "tag3"),
                new TagDto(4L, "tag4")
        );
    }

    @Test
    void getTagById() {
    }

    @Test
    void getAll() throws Exception {
        given(tagService.getAll()).willReturn(tagDtoList);
        this.mockMvc.perform(get("/tag/get_all"))
                .andExpect(status().isOk());
    }

    @Test
    void create() {
    }

    @Test
    void delete() {
    }
}