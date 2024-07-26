package com.attestationhub.sailpoint.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "unique_ids")
@Data
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor
public class UniqueIdEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uniqueIdGenerator")
    @SequenceGenerator(name = "uniqueIdGenerator", sequenceName = "unique_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private Integer tempValue;

}
