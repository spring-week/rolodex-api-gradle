package com.revature.dto;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.revature.models.Address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor // required for Jackson
public class UserDTO {

	@Length(min = 2)
	private @NonNull String firstName;
	private @NonNull String lastName;

	@NotBlank
	@Length(min = 2)
	@Pattern(regexp = "[a-zA-Z][a-zA-Z0-9]*") // username must be alphanumeric
	private @NonNull String username;

	@NotBlank
	private @NonNull String password;

	@Email // checks for an @ symbol
	private @NonNull String email;
 
	private Set<Address> addresses;
}
