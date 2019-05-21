package pl.tscript3r.photogram2.api.v1.dtos;

import java.time.LocalDateTime;

import static pl.tscript3r.photogram2.Consts.*;

public class CommentDtoTest {

    public static CommentDto getDefaultCommentDto() {
        return new CommentDto(ID, ID, USERNAME, ID, CONTENT, LocalDateTime.now());
    }

}