package com.example.bestcarsbackend.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MyFile {
    private Long lastModified;
    private String lastModifiedDate;
    private String name;
    private Long size;
    private String type;
    private String webkitRelativePath;
}
