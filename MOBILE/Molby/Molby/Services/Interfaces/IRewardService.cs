using Molby.Models;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace Molby.Services.Interfaces
{
    public interface IRewardService
    {
        Task<List<Reward>> GetRewardsList();
        Task<Reward> GetRewardById(int id);

    }
}
