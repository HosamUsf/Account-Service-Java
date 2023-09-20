package account.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record ChangePasswordRequest(
        @NotNull
        @JsonProperty("new_password")
        String newPassword) {}
