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

import fm.last.musicbrainz.data.dao.MediumDao;
import fm.last.musicbrainz.data.model.Medium;
import fm.last.musicbrainz.data.model.Recording;
import fm.last.musicbrainz.data.model.Track;
import org.hibernate.type.PostgresUUIDType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository("musicBrainzMediumDaoImpl")
@Transactional("musicBrainzTransactionManager")
public class MediumDaoImpl extends AbstractMusicBrainzHibernateDao<Medium> implements MediumDao {

  public MediumDaoImpl() {
    super(Medium.class);
  }

    @Override
	public Medium getById(int id) {
		return get(id);
	}

	@Override
	public Medium getByGid(UUID gid) {
		return uniqueResult(query(
				"from " + Medium.class.getSimpleName() + " medium left outer join medium.redirectedGids gids"
						+ " where medium.gid = :gid or :gid in (gids)").setParameter("gid", gid, PostgresUUIDType.INSTANCE));
	}


	@Override
	public List<Medium> getByRecording(Recording recording) {
		return list(query(
				"select * from " + Medium.class.getSimpleName()
						 + " medium, " + Track.class.getSimpleName() + " track join track.recording recording where (track.recording = :recordingId) " +
						" join track.medium where track.medium=medium.id").setInteger("recordingId", recording.getId()));
	}


}
