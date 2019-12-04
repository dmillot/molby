using Prism.Commands;
using Prism.Mvvm;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Molby.ViewModels
{
    public class UserRewardViewModel : BindableBase
    {
        public List<GroupProject> GroupProjects { get => _groupProjects; set => _groupProjects = value; }
        private List<GroupProject> _groupProjects;
        public UserRewardViewModel()
        {
            GroupProjects = new List<GroupProject>()
            {
                new GroupProject{ ImageURLProject = "https://www.trzcacak.rs/myfile/full/52-527026_map-overviewback-to-beginning-hide-map-logo-spider.png" , ProjectName = "PROJECT ONE", ProjectNamePoints ="100"},
                new GroupProject{ ImageURLProject = "https://www.trzcacak.rs/myfile/full/52-527026_map-overviewback-to-beginning-hide-map-logo-spider.png" , ProjectName = "PROJECT TOW", ProjectNamePoints ="200"},
                new GroupProject{ ImageURLProject = "https://www.trzcacak.rs/myfile/full/52-527026_map-overviewback-to-beginning-hide-map-logo-spider.png" , ProjectName = "PROJECT BAGDAD", ProjectNamePoints ="1000"},
                new GroupProject{ ImageURLProject = "https://www.trzcacak.rs/myfile/full/52-527026_map-overviewback-to-beginning-hide-map-logo-spider.png" , ProjectName = "PROJECT DELTA", ProjectNamePoints ="500"}
            };
        }
        #region CLASS GROUPS
        public class GroupProject
        {
            public string ProjectName { get; set; }
            public string ImageURLProject { get; set; }
            public string ProjectNamePoints { get; set; }

            public override string ToString()
            {
                return ProjectName;
            }
        }
        #endregion
    }
}
