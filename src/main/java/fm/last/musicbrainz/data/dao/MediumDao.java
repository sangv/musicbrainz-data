// Copyright (c) 2012 Kenzie Lane Mosaic, LLC. All rights reserved.
package fm.last.musicbrainz.data.dao;

import fm.last.musicbrainz.data.model.Medium;
import fm.last.musicbrainz.data.model.Recording;

import java.util.List;

/**
 * The MediumDao
 *
 * @author Sang Venkatraman
 */
public interface MediumDao extends MusicBrainzDao<Medium> {
	List<Medium> getByRecording(Recording recording);
}
