package by.korzun.spotifyspring.system_settigns;

import by.korzun.spotifyspring.exception.SystemSettingIncorrectValueException;
import by.korzun.spotifyspring.exception.SystemSettingTypeMismatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static by.korzun.spotifyspring.system_settigns.SystemSettingsConstants.NUMBER_OF_TRACKS_IN_PLAYLIST;

@Service
@RequiredArgsConstructor
public class SystemSettingsService {

    private final SystemSettingsRepository systemSettingsRepository;

    public Integer findNumberOfTracksInPlaylist() {
        SystemSettings numberOfTracks = systemSettingsRepository.findByName(NUMBER_OF_TRACKS_IN_PLAYLIST);
        return getIntValueOfSystemSetting(numberOfTracks);
    }

    private Integer getIntValueOfSystemSetting(SystemSettings systemSetting) {
        return Integer.parseInt(systemSetting.getValue());
    }

    public void setNumberOfTracksInPlaylist(String value) {
        SystemSettings systemSetting = systemSettingsRepository.findByName(NUMBER_OF_TRACKS_IN_PLAYLIST);
        systemSetting.setValue(validateAndGetNewNumberOfTracksInPlaylist(value));
        systemSettingsRepository.save(systemSetting);
    }

    private String validateAndGetNewNumberOfTracksInPlaylist(String value) {
        try {
            int numberOfTracks = Integer.parseInt(value);
            if (numberOfTracks > 16 || numberOfTracks < 2) {
                throw new SystemSettingIncorrectValueException("The value must be between 2 and 16");
            }
            return String.valueOf(numberOfTracks);
        } catch (NumberFormatException e) {
            throw new SystemSettingTypeMismatchException("Please, enter valid Integer for this variable");
        }
    }
}
