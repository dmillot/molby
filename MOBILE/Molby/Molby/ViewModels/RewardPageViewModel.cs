using Prism.Commands;
using Prism.Mvvm;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Molby.ViewModels
{
    public class RewardPageViewModel : BindableBase
    {
        #region ImageSourceReward
        private string _imageSourceReward;
        public string ImageSourceReward
        {
            get { return _imageSourceReward; }
            set { SetProperty(ref _imageSourceReward, value); }
        }
        #endregion
        #region TitleReward
        private string _titleReward;
        public string TitleReward
        {
            get { return _titleReward; }
            set { SetProperty(ref _titleReward, value); }
        }
        #endregion
        #region ResumeRward
        private string _resumeRward;
        public string ResumeRward
        {
            get { return _resumeRward; }
            set { SetProperty(ref _resumeRward, value); }
        }
        #endregion

        #region XPRequierd
        private string _xPRequierd;
        public string XPRequierd
        {
            get { return _xPRequierd; }
            set { SetProperty(ref _xPRequierd, value); }
        }
        #endregion
        #region XPPrice
        private string _xPPrice;
        public string XPPrice
        {
            get { return _xPPrice; }
            set { SetProperty(ref _xPPrice, value); }
        }
        #endregion

        public List<RewardListGroup> RewardListGroups { get => _rewardListGroup; set => _rewardListGroup = value; }
        private List<RewardListGroup> _rewardListGroup;

        public RewardPageViewModel()
        {
            ImageSourceReward = "http://static.thiriet.com/data/common_public/gallery_images/site/18756/18774/73090.jpg";
            TitleReward = "Croissant + Café";
            ResumeRward = "1 Croissant + 1 Café offert le matin de la part du Client, Mr Royer";
            XPPrice = "2 500";
            XPRequierd = "12 000";
            RewardListGroups = new List<RewardListGroup>()
            {
                new RewardListGroup{ ImageUrl = "https://icon-library.net/images/arrow-icon-white/arrow-icon-white-0.jpg" , Name = "Damien"},
                new RewardListGroup{ ImageUrl = "https://icon-library.net/images/arrow-icon-white/arrow-icon-white-0.jpg" , Name = "Georges"},
                new RewardListGroup{ ImageUrl = "https://icon-library.net/images/arrow-icon-white/arrow-icon-white-0.jpg" , Name = "Albert"},
                new RewardListGroup{ ImageUrl = "https://icon-library.net/images/arrow-icon-white/arrow-icon-white-0.jpg" , Name = "Michel"}
            };
        }

        #region CLASS RewardListGroup
        public class RewardListGroup
        {
            public string Name { get; set; }
            public string ImageUrl { get; set; }

            public override string ToString()
            {
                return Name;
            }
        }
        #endregion
    }
}
