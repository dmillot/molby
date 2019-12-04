using Prism.Commands;
using Prism.Mvvm;
using Prism.Navigation;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Molby.ViewModels
{
    public class MainPageViewModel : BindableBase
    {
        #region Title
        private string _title;
        public string Title
        {
            get { return _title; }
            set { SetProperty(ref _title, value); }
        }
        #endregion
        #region UrlImage
        private string _urlImage;
        public string UrlImage
        {
            get { return _urlImage; }
            set { SetProperty(ref _urlImage, value); }
        }
        #endregion
        #region Names
        private string _names;
        public string Names
        {
            get { return _names; }
            set { SetProperty(ref _names, value); }
        }
        #endregion
        #region Promotion
        private string _promotion;
        public string Promotion
        {
            get { return _promotion; }
            set { SetProperty(ref _promotion, value); }
        }
        #endregion
        #region NameLevel
        private string _nameLevel;
        public string NameLevel
        {
            get { return _nameLevel; }
            set { SetProperty(ref _nameLevel, value); }
        }
        #endregion
        #region LevelImage
        private string _levelImage;
        public string LevelImage
        {
            get { return _levelImage; }
            set { SetProperty(ref _levelImage, value); }
        }
        #endregion
        #region XPerience
        private string _xPerience;
        public string XPerience
        {
            get { return _xPerience; }
            set { SetProperty(ref _xPerience, value); }
        }
        #endregion

        //REWARDS WON
        #region REWARD IMAGE
        private string _rewardImage;
        public string RewardImage
        {
            get { return _rewardImage; }
            set { SetProperty(ref _rewardImage, value); }
        }
        #endregion

        //GROUPS
        #region ArrowImage
        private string _arrowImage;
        public string ArrowImage
        {
            get { return _arrowImage; }
            set { SetProperty(ref _arrowImage, value); }
        }
        #endregion

        //FOOTER LOGOS
        #region Footer HOME
        private string _ibHomeLogo;
        public string IBHome
        {
            get { return _ibHomeLogo; }
            set { SetProperty(ref _ibHomeLogo, value); }
        }
        #endregion
        #region Footer Search
        private string _ibSearchLogo;
        public string IBSearch
        {
            get { return _ibSearchLogo; }
            set { SetProperty(ref _ibSearchLogo, value); }
        }
        #endregion
        #region Footer Rewards
        private string _ibRewardLogo;
        public string IBReward
        {
            get { return _ibRewardLogo; }
            set { SetProperty(ref _ibRewardLogo, value); }
        }
        #endregion
        #region Footer PROFIL
        private string _ibProfilLogo;
        public string IBProfil
        {
            get { return _ibProfilLogo; }
            set { SetProperty(ref _ibProfilLogo, value); }
        }
        #endregion
        #region Footer Add
        private string _ibAddLogo;
        public string IBAdd
        {
            get { return _ibAddLogo; }
            set { SetProperty(ref _ibAddLogo, value); }
        }
        #endregion

        #region Footer
        private string _footer;
        public string Footer
        {
            get { return _footer; }
            set { SetProperty(ref _footer, value); }
        }
        #endregion

        public List<LevelGroup> LevelGroups { get => _levelGroups; set => _levelGroups = value; }
        private List<LevelGroup> _levelGroups;

        public MainPageViewModel()
        {
            Title = "Main Page";
            UrlImage = "http://ovejarosa.com/wp-content/uploads/2019/09/Valentina-Sampaio.png";
            Names = "Damien MILLOT";
            Promotion = "DIIAGE DEV 2022";
            NameLevel = "Naruto";
            LevelImage = "http://static.hitek.fr/img/actualite/i_naruto-1.jpg";
            XPerience = "2 700";

            LevelGroups = new List<LevelGroup>()
            {
                new LevelGroup{ LevelImage = "http://static.flylevel.com/level-logo-1200x630.png?93910" , LevelName = "LEVEL ONE", LevelXP ="100"},
                new LevelGroup{ LevelImage = "http://static.flylevel.com/level-logo-1200x630.png?93910" , LevelName = "LEVEL TOW", LevelXP ="200"},
                new LevelGroup{ LevelImage = "http://static.flylevel.com/level-logo-1200x630.png?93910" , LevelName = "LEVEL BAGDAD", LevelXP ="1000"},
                new LevelGroup{ LevelImage = "http://static.flylevel.com/level-logo-1200x630.png?93910" , LevelName = "LEVEL DELTA", LevelXP ="500"}
            };

            //REWARDS WON
            RewardImage = "http://static.thiriet.com/data/common_public/gallery_images/site/18756/18774/73090.jpg";

            //GROUPS
            ArrowImage = "http://icon-library.net/images/white-arrow-icon-png/white-arrow-icon-png-21.jpg";

            //FOOTER
            IBHome = "http://bit.ly/2rrgbhn";
            IBSearch = "http://aux.iconspalace.com/uploads/1352231607285850304.png";
            IBAdd = "http://bit.ly/34nuS3w";
            IBReward = "http://cdn2.iconfinder.com/data/icons/vote-rewards/24/vote-medal-2-512.png";
            IBProfil = "http://carlisletheacarlisletheatre.org/images/about-icon-profile-6.png";

        }

        #region CLASS LevelGroup
        public class LevelGroup
        {
            public string LevelName { get; set; }
            public string LevelImage { get; set; }
            public string LevelXP { get; set; }

            public override string ToString()
            {
                return LevelName;
            }
        }
        #endregion
    }
}
