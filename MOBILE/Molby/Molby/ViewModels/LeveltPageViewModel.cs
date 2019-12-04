using Prism.Commands;
using Prism.Mvvm;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Molby.ViewModels
{
    public class LeveltPageViewModel : BindableBase
    {
        #region LevelImage
        private string _levelImage;
        public string LevelImage
        {
            get { return _levelImage; }
            set { SetProperty(ref _levelImage, value); }
        }
        #endregion
        #region LevelName
        private string _levelName;
        public string LevelName
        {
            get { return _levelName; }
            set { SetProperty(ref _levelName, value); }
        }
        #endregion
        #region LevelStep
        private string _levelStep;
        public string LevelStep
        {
            get { return _levelStep; }
            set { SetProperty(ref _levelStep, value); }
        }
        #endregion
        #region LevelInfo
        private string _levelInfo;
        public string LevelInfo
        {
            get { return _levelInfo; }
            set { SetProperty(ref _levelInfo, value); }
        }
        #endregion
        #region XPRequierd
        private string _xpRequierd;
        public string XPRequierd
        {
            get { return _xpRequierd; }
            set { SetProperty(ref _xpRequierd, value); }
        }
        #endregion
        #region XPPrice
        private string _xpPrice;
        public string XPPrice
        {
            get { return _xpPrice; }
            set { SetProperty(ref _xpPrice, value); }
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
        public LeveltPageViewModel()
        {
            LevelImage = "http://static.hitek.fr/img/actualite/i_naruto-1.jpg";
            LevelName = "Naruto";
            LevelStep = "Level 4";
            LevelInfo = "Le Lorem Ipsum est simplement du faux texte employé dans la composition et la mise en page avant impression.";
            XPPrice = "2 000";
            XPRequierd = "20 000";

            //FOOTER
            IBHome = "http://bit.ly/2rrgbhn";
            IBSearch = "http://aux.iconspalace.com/uploads/1352231607285850304.png";
            IBAdd = "http://bit.ly/34nuS3w";
            IBReward = "http://cdn2.iconfinder.com/data/icons/vote-rewards/24/vote-medal-2-512.png";
            IBProfil = "http://carlisletheacarlisletheatre.org/images/about-icon-profile-6.png";
        }
    }
}
