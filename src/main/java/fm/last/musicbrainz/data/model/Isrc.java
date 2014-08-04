/*
 * Copyright 2013 The musicbrainz-data Authors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package fm.last.musicbrainz.data.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Access(AccessType.FIELD)
@Entity
@Table(name = "isrc", schema = "musicbrainz")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Isrc {

  @Id
  @Column(name = "id")
  private int id;

  @OneToOne(targetEntity = Recording.class, optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "recording")
  private Recording recording;

  public int getId() {
    return id;
  }

  public Recording getRecording() {
    return recording;
  }

}
