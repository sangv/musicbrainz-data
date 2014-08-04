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
package fm.last.musicbrainz.data.dao.impl;

import fm.last.musicbrainz.data.dao.ReleaseDao;
import fm.last.musicbrainz.data.model.Artist;
import fm.last.musicbrainz.data.model.Recording;
import fm.last.musicbrainz.data.model.Release;
import org.hibernate.type.PostgresUUIDType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository("musicBrainzReleaseDaoImpl")
@Transactional("musicBrainzTransactionManager")
public class ReleaseDaoImpl extends AbstractMusicBrainzHibernateDao<Release> implements ReleaseDao {

  public ReleaseDaoImpl() {
    super(Release.class);
  }

  @Override
  public Release getById(int id) {
    return get(id);
  }

  @Override
  public Release getByGid(UUID gid) {
    return uniqueResult(query(
        "from " + Release.class.getSimpleName() + " release left outer join release.redirectedGids gids"
            + " where release.gid = :gid or :gid in (gids)").setParameter("gid", gid, PostgresUUIDType.INSTANCE));
  }

  @Override
  public List<Release> getByArtist(Artist artist) {
    return list(query(
        "select release from " + Release.class.getSimpleName()
            + " release join release.artistCredit.artistCreditNames artistCreditNames"
            + " where artistCreditNames.artist.id = :artistId").setInteger("artistId", artist.getId()));
  }

  @Override
  public List<Release> getByArtistAndName(Artist artist, String releaseName) {
	return list(query(
		"select release from " + Release.class.getName()
			+ " release join release.artistCredit.artistCreditNames artistCreditNames"
			+ " where artistCreditNames.artist.id = :artistId and upper(release.name) = upper(:name)").setInteger(
		"artistId", artist.getId()).setString("name", releaseName));
  }

	@Override
	public List<Release> getByNameAndArtistNames(String releaseName, String... artistNames) {//FIXME it checks only the first artist
		return list(query(
				"select release from " + Release.class.getName()
						+ " release join release.artistCredit.artistCreditNames artistCreditNames"
						+ " where upper(artistCreditNames.artist.name) = upper(:artistName) and upper(release.name) = upper(:name)").setString(
				"artistName", artistNames[0]).setString("name", releaseName));
		/*return list(query("select release from Release release join artist_credit_name artistCreditNames on artistCreditNames.name = :artistName and upper(release.name) = upper(:releaseName)").setString(
				"artistName", artistNames[0]).setString("releaseName", releaseName));*/
	}

	@Override
	public List<Release> getByRecording(Recording recording) {//TODO use joins instead
		return list(query(
				"SELECT r from Release r where r.id IN (select release FROM Medium m where m.id IN (select distinct t.medium from Track t where t.recording = :recordingId))").setInteger("recordingId", recording.getId()));
	}

	@Override
	public List<Release> getByIsrc(String isrc) {//TODO use joins instead
		return list(query(
				"SELECT r from Release r where r.id IN (select release FROM Medium m where m.id IN (select distinct t.medium from Track t where t.recording IN (SELECT recording from Isrc  where isrc = :isrc)))").setString("isrc", isrc));
	}

}
