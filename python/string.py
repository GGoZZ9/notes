import os
import re
import time
import sys

filePath = "/opt/data/repository/quality-control_cucumber-common-library-release-1.0.0"
repoFolderName = filePath.split('/')[-1]
namespace = repoFolderName[:repoFolderName.index("_")]
repoName = repoFolderName.split("_")[1]
filePath.upper()
filePath.find()

all_list = [1, 2, 3]

for i in range(len(all_list)):
    print()

if __name__ == '__main__':
    print(repoFolderName)
    print(repoFolderName.index("_"))
    print(namespace)
    print(repoName)
    print(os.path.isdir("/Users"))
    print(os.path.join("/Users", "1"))
    print(len(all_list))
    print(range(len(all_list)))
    for i in range(len(all_list)):
        print(i)
