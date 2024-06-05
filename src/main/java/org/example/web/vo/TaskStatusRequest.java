package org.example.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.ToString;
import org.example.constants.TaskStatus;

@Getter
@ToString

public class TaskStatusRequest {

    private TaskStatus status;
}
