package com.revature.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data @AllArgsConstructor @NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Length(min = 2)
	private String firstName;
	private String lastName;

	@NotBlank
	@Length(min = 2)
	@Pattern(regexp = "[a-zA-Z][a-zA-Z0-9]*") // username must be alphanumeric
	private String username;

	@NotBlank
	private String password;

	@Email // checks for an @ symbol
	private String email;

	/**
	 * Establish a ManyToMany relationship between Address and User many users may
	 * have many addresses and vice versa
	 */
	@ManyToMany
	@JoinTable(name = "users_address", 
	joinColumns = @JoinColumn(name = "user_id"), 
	inverseJoinColumns = @JoinColumn(name = "address_id"))
	private Set<Address> addresses;

	/**
	 * Regarding the validation constraints on some of the fields
	 * 
	 * @NotNull: a constrained CharSequence, Collection, Map, or Array is valid as
	 *           long as it's not null, but it can be empty.
	 * @NotEmpty: a constrained CharSequence, Collection, Map, or Array is valid as
	 *            long as it's not null, and its size/length is greater than zero.
	 * @NotBlank: a constrained String is valid as long as it's not null, and the
	 *            trimmed length is greater than zero.
	 */

}
