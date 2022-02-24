package io.dowlath.employee.model;

import lombok.*;

import javax.persistence.*;

/***
 * Author : Dowlath Basha G
 * Date   : 2/21/2022
 * Time   : 10:28 PM
 ***/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
@Builder

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="first_name",nullable = false)
    private String firstName;

    @Column(name="last_name",nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;
}
