package org.udg.pds.springtodo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.udg.pds.springtodo.serializer.JsonDateDeserializer;
import org.udg.pds.springtodo.serializer.JsonDateSerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
// This tells JAXB that it has to ignore getters and setters and only use fields for JSON marshaling/unmarshaling
public class Task implements Serializable {
  /**
   * Default value included to remove warning. Remove or modify at will.
   **/
  private static final long serialVersionUID = 1L;

  public Task() {
  }

  public Task(Date dateCreated, Date dateLimit, Boolean completed, String text) {
    this.dateCreated = dateCreated;
    this.dateLimit = dateLimit;
    this.completed = completed;
    this.text = text;
  }

  // This tells JAXB that this field can be used as ID
  // Since XmlID can only be used on Strings, we need to use LongAdapter to transform Long <-> String
  @Id
  // Don't forget to use the extra argument "strategy = GenerationType.IDENTITY" to get AUTO_INCREMENT
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Date dateCreated;

  private Date dateLimit;

  private Boolean completed;

  private String text;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "fk_perruquer")
  private Perruquer perruquer;

  @Column(name = "fk_perruquer", insertable = false, updatable = false)
  private Long perruquerId;

  @ManyToMany(cascade = CascadeType.ALL)
  private Collection<Tag> tags = new ArrayList<>();

  @JsonView(Views.Private.class)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @JsonIgnore
  public Perruquer getPerruquer() {
    return perruquer;
  }

  public void setPerruquer(Perruquer perruquer) {
    this.perruquer = perruquer;
  }

  public void addTag(Tag tag) {
    tags.add(tag);
  }

  @JsonView(Views.Complete.class)
  public Collection<Tag> getTags() {
    tags.size();
    return tags;
  }

  @JsonView(Views.Private.class)
  public Boolean getCompleted() {
    return completed;
  }

  @JsonView(Views.Private.class)
  public String getText() {
    return text;
  }

  @JsonView(Views.Complete.class)
  public long getPerruquerId() {
    return perruquerId;
  }

  @JsonView(Views.Private.class)
  @JsonSerialize(using = JsonDateSerializer.class)
  @JsonDeserialize(as= JsonDateDeserializer.class)
  public Date getDateCreated() {
    return dateCreated;
  }

  @JsonView(Views.Private.class)
  @JsonSerialize(using = JsonDateSerializer.class)
  @JsonDeserialize(as= JsonDateDeserializer.class)
  public Date getDateLimit() {
    return dateLimit;
  }
}
