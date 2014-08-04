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
package fm.last.musicbrainz.data.dao;

import fm.last.musicbrainz.data.model.Artist;
import fm.last.musicbrainz.data.model.Recording;
import fm.last.musicbrainz.data.model.Release;

import java.util.List;

/**
 * Provides access to {@link Release}s.
 */
public interface ReleaseDao extends MusicBrainzDao<Release> {

  /**
   * Also returns {@link Release}s where the {@link Artist} collaborated with other {@link Artist}s.
   * 
   * @return Empty list if {@link Artist} has no {@link Release}s
   */
  List<Release> getByArtist(Artist artist);

  /**
   * Ignores the casing of {@code releaseName}.
   * 
   * @return Empty list if no {@link Release}s are found
   */
  List<Release> getByArtistAndName(Artist artist, String releaseName);

  List<Release> getByNameAndArtistNames(String releaseName, String... artistNames);

   List<Release> getByRecording(Recording recording);

	List<Release> getByIsrc(String isrc);
}
