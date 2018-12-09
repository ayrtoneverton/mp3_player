package com.player.model;

public interface Playable {
	//@ public model instance PlayList playList;

	//@ ensures playList.getMusics().size() == \result.getMusics().size();
	//@ ensures (\forall int i; i >= 0 && i < playList.getMusics().size(); \result.getMusics().get(i) == playList.getMusics().get(i));
	//@ pure
	public PlayList getPlayList();
}
