using Molby.Models;
using Molby.Services;
using Prism;
using Prism.Commands;
using Prism.Mvvm;
using Prism.Navigation;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;

namespace Molby.ViewModels
{
    public class GroupPageViewModel : BindableBase, IActiveAware, INavigationAware
    {
        private readonly GroupService _groupService;
        private ObservableCollection<Group> _groupsList;
        private Group _group;
        private bool _isActive;

        public DelegateCommand<object> NavigateToProfile { get; set; }
        public INavigationService NavigationService { get; set; }

        public event EventHandler IsActiveChanged;

        public bool IsActive
        {
            get { return _isActive; }
            set
            {
                _isActive = value;
                OnActiveTabChangedAsync();
            }
        }

        private async void OnActiveTabChangedAsync()
        {
            if (IsActive)
            {
                var result = await _groupService.GetGroupById(2);
                Group = result as Group;
            }
        }

        public GroupPageViewModel(GroupService groupService, INavigationService navigationService)
        {
            _groupService = groupService;
            this.NavigationService = navigationService;
            this.NavigateToProfile = new DelegateCommand<object>(GoToProfile);
        }

        void GoToProfile(object parameters)
        {
            var navParam = new NavigationParameters();
            navParam.Add("id", parameters);
            this.NavigationService.NavigateAsync("ProfilePage", navParam);
        }

        public void OnNavigatedFrom(INavigationParameters parameters)
        {
            throw new NotImplementedException();
        }

        public void OnNavigatedTo(INavigationParameters parameters)
        {
        }

        public Group Group
        {
            get { return _group; }
            set { SetProperty(ref _group, value); }
        }

        public ObservableCollection<Group> GroupsList
        {
            get { return _groupsList; }
            set { SetProperty(ref _groupsList, value); }
        }
    }
}