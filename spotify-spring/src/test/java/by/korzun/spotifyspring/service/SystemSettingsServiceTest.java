package by.korzun.spotifyspring.service;

import by.korzun.spotifyspring.exception.SystemSettingIncorrectValueException;
import by.korzun.spotifyspring.exception.SystemSettingTypeMismatchException;
import by.korzun.spotifyspring.system_settigns.SystemSettings;
import by.korzun.spotifyspring.system_settigns.SystemSettingsRepository;
import by.korzun.spotifyspring.system_settigns.SystemSettingsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static by.korzun.spotifyspring.system_settigns.SystemSettingsConstants.NUMBER_OF_TRACKS_IN_PLAYLIST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SystemSettingsServiceTest {

    @Mock
    private SystemSettingsRepository systemSettingsRepository;
    @InjectMocks
    private SystemSettingsService systemSettingsService;

    private static SystemSettings SYSTEM_SETTING;

    @BeforeEach
    public void init() {
        SYSTEM_SETTING = new SystemSettings()
                .setId(1L)
                .setDescription("Desc.")
                .setName("numberOfTracks")
                .setValue("3")
                .setType("INT");
    }

    @Test
    void findNumberOfTracksInPlaylist() {
        when(systemSettingsRepository.findByName(NUMBER_OF_TRACKS_IN_PLAYLIST))
                .thenReturn(SYSTEM_SETTING);
        Integer systemSettingValue = systemSettingsService.findNumberOfTracksInPlaylist();

        assertEquals(SYSTEM_SETTING.getValue(), String.valueOf(systemSettingValue));
    }

    @Test
    void setNumberOfTracksInPlaylist() {
        when(systemSettingsRepository.findByName(NUMBER_OF_TRACKS_IN_PLAYLIST))
                .thenReturn(SYSTEM_SETTING);
        systemSettingsService.setNumberOfTracksInPlaylist("5");

        assertEquals("5", SYSTEM_SETTING.getValue());
    }

    @Test
    void throwException_WhenPassUnacceptableValueToSetNumberOfTracks() {
        when(systemSettingsRepository.findByName(NUMBER_OF_TRACKS_IN_PLAYLIST))
                .thenReturn(SYSTEM_SETTING);

        Exception e = assertThrows(
                SystemSettingIncorrectValueException.class,
                () -> systemSettingsService.setNumberOfTracksInPlaylist("1000000")
        );
        assertEquals("The value must be between 2 and 16", e.getMessage());
    }

    @Test
    void throwException_WhenPassStringToSetNumberOfTracks() {
        when(systemSettingsRepository.findByName(NUMBER_OF_TRACKS_IN_PLAYLIST))
                .thenReturn(SYSTEM_SETTING);

        Exception e = assertThrows(
                SystemSettingTypeMismatchException.class,
                () -> systemSettingsService.setNumberOfTracksInPlaylist("str")
        );
        assertEquals("Please, enter valid Integer for this variable", e.getMessage());
    }
}
