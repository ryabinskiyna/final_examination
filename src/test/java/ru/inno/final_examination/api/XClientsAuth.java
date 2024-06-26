package ru.inno.final_examination.api;

import ru.inno.final_examination.model.UserInfo;

import java.io.IOException;

public interface XClientsAuth {
    UserInfo auth() throws IOException;
}