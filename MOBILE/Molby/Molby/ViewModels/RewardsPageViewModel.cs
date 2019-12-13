using Molby.Models;
using Molby.Services.Interfaces;
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
    public class RewardsPageViewModel : BindableBase, IActiveAware, INavigationAware
    {
        private readonly RewardService _rewardService;
        private ObservableCollection<Reward> _rewardsList;
        private bool _isActive;
        public bool IsActive
        {
            get { return _isActive; }
            set
            {
                _isActive = value;
                OnActiveTabChangedAsync();
            }
        }
        public ObservableCollection<Reward> RewardsList
        {
            get { return _rewardsList; }
            set { SetProperty(ref _rewardsList, value); }
        }
        public RewardsPageViewModel(RewardService rewardService)
        {
            this._rewardService = rewardService;
        }

        public event EventHandler IsActiveChanged;

        private async void OnActiveTabChangedAsync()
        {
            if (IsActive)
            {
                var result = await _rewardService.GetRewardsList();
                RewardsList = new ObservableCollection<Reward>(result);
            }
        }

        public void OnNavigatedFrom(INavigationParameters parameters)
        {
            throw new NotImplementedException();
        }

        public void OnNavigatedTo(INavigationParameters parameters)
        {
            throw new NotImplementedException();
        }
    }
}
