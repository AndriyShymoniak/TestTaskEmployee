package com.shymoniak.testtask.entity;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private String fullName;
    private String department;
    private int salary;
}
