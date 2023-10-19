package antifraud.http.response;

import antifraud.http.View;
import antifraud.http.model.AppUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

@Getter
public class UserCreatedResponse extends ResponseEntity<String> {

    public UserCreatedResponse(AppUser user) {
        super(map(user), HttpStatus.CREATED);

    }
    @SneakyThrows // Should not throw
    private static String map(AppUser user) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        return  mapper
                .writerWithView(View.UserView.class)
                .writeValueAsString(user);
    }
}
