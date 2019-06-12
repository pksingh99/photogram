package pl.tscript3r.photogram2.domains;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.tscript3r.photogram2.exceptions.IgnoredPhotogramException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static pl.tscript3r.photogram2.Consts.*;
import static pl.tscript3r.photogram2.domains.UserTest.getDefaultUser;
import static pl.tscript3r.photogram2.domains.UserTest.getSecondUser;

@DisplayName("Post")
public class PostTest {

    private static final String FIELD_CHANGED_REMOVED_EXCEPTION = "Probably field name has been changed, or removed - " +
            "exception caused by reflexion";

    public static Post getDefaultPost() {
        var result = new Post(getDefaultUser(), CAPTION, LOCATION, IMAGES_COUNT);
        result.setId(ID);
        result.addImageId(IMAGE_ID);
        for (int i = 0; i < LIKES; i++)
            result.incrementLikes();
        for (int i = 0; i < DISLIKES; i++)
            result.incrementDislikes();
        try {
            FieldUtils.writeField(result, "creationDate", LocalDateTime.now(), true);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(FIELD_CHANGED_REMOVED_EXCEPTION);
        }
        result.getComments().add(new Comment(getDefaultUser(), result, CONTENT));
        return result;
    }

    public static Post getSecondPost() {
        var result = new Post(getSecondUser(), SECOND_CAPTION, SECOND_LOCATION, IMAGES_COUNT);
        result.setId(SECOND_ID);
        result.addImageId(SECOND_IMAGE_ID);
        for (int i = 0; i < LIKES; i++)
            result.incrementLikes();
        try {
            FieldUtils.writeField(result, "creationDate", LocalDateTime.now(), true);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(FIELD_CHANGED_REMOVED_EXCEPTION);
        }
        return result;
    }

    @Test
    @DisplayName("Equals only with same ID")
    void equals() {
        var post = getDefaultPost();
        var secondPost = getSecondPost();
        secondPost.setId(post.getId());
        assertEquals(post, secondPost);
    }

    @Test
    @DisplayName("Not equals with only different ID")
    void notEqualsWithDifferentId() {
        var post = getDefaultPost();
        var almostSamePost = getDefaultPost();
        almostSamePost.setId(post.getId() + 1L);
        assertNotEquals(post, almostSamePost);
    }

    @Test
    @DisplayName("Adding imageId together with valid as true")
    void setImageIdAndValidAsTrue() {
        var post = getPostWithImageIdAsNull();
        post.addImageId(ID);
        assertEquals(ID, post.getImages().iterator().next().getImageId());
        assertTrue(post.getValid());
    }

    private Post getPostWithImageIdAsNull() {
        return new Post(getDefaultUser(), CAPTION, LOCATION, IMAGES_COUNT);
    }

    @Test
    @DisplayName("Set null imageId (valid should be as false)")
    void setImageIdWithNullArgument() {
        var post = getPostWithImageIdAsNull();
        post.addImageId(null);
        assertTrue(post.getImages().isEmpty());
        assertFalse(post.getValid());
    }

    @Test
    @DisplayName("Set to low value imageId (valid should be as false)")
    void setImageIdWithToLowValueArgument() {
        var post = getPostWithImageIdAsNull();
        post.addImageId(-1L);
        assertTrue(post.getImages().isEmpty());
        assertFalse(post.getValid());
    }

    @Test
    @DisplayName("Increment likes")
    void incrementLikes() {
        var post = getDefaultPost();
        post.incrementLikes();
        assertEquals(2, post.getLikes().intValue());
    }

    @Test
    @DisplayName("Decrement likes")
    void incrementDislikes() {
        var post = getDefaultPost();
        post.incrementDislikes();
        assertEquals(2, post.getDislikes().intValue());
    }

    @Test
    @DisplayName("Decrement likes")
    void decrementLikes() {
        var post = getDefaultPost();
        post.decrementLikes();
        assertEquals(0, post.getLikes().intValue());
    }

    @Test
    @DisplayName("Decrement dislikes")
    void decrementDislikes() {
        var post = getDefaultPost();
        post.decrementDislikes();
        assertEquals(0, post.getDislikes().intValue());
    }

    @Test
    @DisplayName("Decrement likes equal 0")
    void decrementLikesEqualZero() {
        var post = getDefaultPost();
        post.decrementLikes();
        post.decrementLikes();
        assertEquals(0, post.getLikes().intValue());
    }

    @Test
    @DisplayName("Decrement dislikes equal 0")
    void decrementDislikesEqualZero() {
        var post = getDefaultPost();
        post.decrementDislikes();
        post.decrementDislikes();
        assertEquals(0, post.getDislikes().intValue());
    }

    @Test
    @DisplayName("Add more than expected images")
    void addToManyImages() {
        var post = getDefaultPost();
        assertThrows(IgnoredPhotogramException.class, () -> post.addImageId(SECOND_IMAGE_ID));
    }

}