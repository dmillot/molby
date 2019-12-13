using Molby.Models;
using Molby.Services;
using Prism;
using Prism.Commands;
using Prism.Mvvm;
using Prism.Navigation;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Molby.ViewModels
{
    public class ProfilePageViewModel : BindableBase, IActiveAware, INavigationAware
    {
        private readonly UserService _userService;
        private User _user;
        private Level _userLevel;
        private bool _isActive;

        public bool IsActive { get { return _isActive; } set { _isActive = value; OnActiveTabChangedAsync(); } }
        public User User { get { return _user; } set { SetProperty(ref _user, value); } }
        public Level UserLevel { get { return _userLevel; } set { SetProperty(ref _userLevel, value); } }
        public DelegateCommand<object> NavigateToLevelDesc { get; set; }
        public INavigationService NavigationService { get; set; }

        public ProfilePageViewModel(UserService userService, INavigationService navigationService)
        {
            this._userService = userService;
            this.NavigationService = navigationService;
            this.NavigateToLevelDesc = new DelegateCommand<object>(GoToLevelDesc);
        }

        void GoToLevelDesc(object parameters)
        {
            //var levelId = parameters;
            var navParam = new NavigationParameters();
            navParam.Add("id", parameters);
            this.NavigationService.NavigateAsync("LevelDescriptionPage", navParam);
        }

        private async void OnActiveTabChangedAsync()
        {
            if (IsActive)
            {
                var result = await _userService.GetUserById(1);
                User = result as User;

                var level = await _userService.GetUserCurrentLevel(1);
                UserLevel = level as Level;
            }
        }

        public void OnNavigatedFrom(INavigationParameters parameters)
        {
            throw new NotImplementedException();
        }

        public async void OnNavigatedTo(INavigationParameters parameters)
        {
            Console.WriteLine("ok");
        }

        public event EventHandler IsActiveChanged;
    }
}
