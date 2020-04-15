package ch.course223.advanced.core;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class ExtendedEntity {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Generated(GenerationTime.ALWAYS)
	@Column(name = "id")
	private String id;

	public ExtendedEntity() {
	}

	public ExtendedEntity(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public ExtendedEntity setId(String id) {
		this.id = id;
		return this;
	}
}