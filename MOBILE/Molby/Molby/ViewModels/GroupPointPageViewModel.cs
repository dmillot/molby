using Prism.Commands;
using Prism.Mvvm;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Molby.ViewModels
{
    public class GroupPointPageViewModel : BindableBase
    {
        #region ImageSource1
        private string _imageSource1;
        public string ImageSource1
        {
            get { return _imageSource1; }
            set { SetProperty(ref _imageSource1, value); }
        }
        #endregion
        #region ImageSource2
        private string _imageSource2;
        public string ImageSource2
        {
            get { return _imageSource2; }
            set { SetProperty(ref _imageSource2, value); }
        }
        #endregion
        #region ImageSource2
        private string _imageSource3;
        public string ImageSource3
        {
            get { return _imageSource3; }
            set { SetProperty(ref _imageSource3, value); }
        }
        #endregion
        #region ImageSource4
        private string _imageSource4;
        public string ImageSource4
        {
            get { return _imageSource4; }
            set { SetProperty(ref _imageSource4, value); }
        }
        #endregion

        #region ProjectName
        private string _labelTimeToProject;
        public string ProjectName
        {
            get { return _labelTimeToProject; }
            set { SetProperty(ref _labelTimeToProject, value); }
        }
        #endregion
        #region LabelEXPToProject
        private string _labelEXPToProject;
        public string LabelEXPToProject
        {
            get { return _labelEXPToProject; }
            set { SetProperty(ref _labelEXPToProject, value); }
        }


        #endregion

        public List<ProjectGroupPoint> ProjectGroupPoints { get => _projectGroupPoint; set => _projectGroupPoint = value; }
        private List<ProjectGroupPoint> _projectGroupPoint;

        public GroupPointPageViewModel()
        {
            ImageSource1 = "http://ovejarosa.com/wp-content/uploads/2019/09/Valentina-Sampaio.png";
            ImageSource2 = "http://ovejarosa.com/wp-content/uploads/2019/09/Valentina-Sampaio.png";
            ImageSource3 = "http://ovejarosa.com/wp-content/uploads/2019/09/Valentina-Sampaio.png";
            ImageSource4 = "http://ovejarosa.com/wp-content/uploads/2019/09/Valentina-Sampaio.png";

            ProjectGroupPoints = new List<ProjectGroupPoint>()
            {
                new ProjectGroupPoint{ ImageURLProject = "https://icon-library.net/images/arrow-icon-white/arrow-icon-white-0.jpg" , ProjectName = "PROJECT ONE"},
                new ProjectGroupPoint{ ImageURLProject = "https://icon-library.net/images/arrow-icon-white/arrow-icon-white-0.jpg" , ProjectName = "PROJECT TOW"},
                new ProjectGroupPoint{ ImageURLProject = "https://icon-library.net/images/arrow-icon-white/arrow-icon-white-0.jpg" , ProjectName = "PROJECT BAGDAD"},
                new ProjectGroupPoint{ ImageURLProject = "https://icon-library.net/images/arrow-icon-white/arrow-icon-white-0.jpg" , ProjectName = "PROJECT DELTA"}
            };
        }

        #region CLASS GROUPS
        public class ProjectGroupPoint
        {
            public string ProjectName { get; set; }
            public string ImageURLProject { get; set; }

            public override string ToString()
            {
                return ProjectName;
            }
        }
        #endregion
    }
}
